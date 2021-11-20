package db.dbspringtemplate.rest.service;

import db.dbspringtemplate.model.Sample;
import db.dbspringtemplate.repository.SampleRepository;
import db.dbspringtemplate.rest.error.RestErrorModel;
import db.dbspringtemplate.rest.error.RestException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/samples")
@PreAuthorize("hasAuthority('admin')")
public class SampleRestService {

    private final SampleRepository sampleRepository;

    @Autowired
    public SampleRestService(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    @GetMapping("")
    @ApiOperation("List all samples")
    public List<Long> listAll() {
        return this.sampleRepository.getAllIds();
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve a sample by id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Sample not found", response = RestErrorModel.class)
    })
    public Sample getById(@PathVariable Long id) {
        Optional<Sample> sample = this.sampleRepository.findById(id);
        return sample.orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Sample not found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete a sample by id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Sample not found", response = RestErrorModel.class)
    })
    public void deleteById(@PathVariable Long id) {
        try {
            this.sampleRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RestException(HttpStatus.NOT_FOUND, "Sample not found");
        }
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Save a new sample")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = RestErrorModel.class),
            @ApiResponse(code = 409, message = "Conflict", response = RestErrorModel.class)
    })
    public Sample create(@RequestBody @Valid SampleApiRequest request) {
        this.sampleRepository.findByName(request.name).ifPresent(s -> {
            throw new RestException(HttpStatus.CONFLICT, "Another sample with the same name already exists in the database");
        });
        Sample sample = new Sample();
        sample.setName(request.name);
        sample.setDescription(request.description);
        return this.sampleRepository.save(sample);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update an existing sample")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = RestErrorModel.class),
            @ApiResponse(code = 404, message = "Sample not found", response = RestErrorModel.class),
            @ApiResponse(code = 409, message = "Conflict", response = RestErrorModel.class)
    })
    public Sample updateById(@PathVariable Long id, @RequestBody @Valid SampleApiRequest request) {
        Optional<Sample> sampleOptional = this.sampleRepository.findById(id);
        if (sampleOptional.isEmpty()) {
            throw new RestException(HttpStatus.NOT_FOUND, "Sample not found");
        }

        Sample sample = sampleOptional.get();
        if (!sample.getName().equals(request.name)) {
            this.sampleRepository.findByName(request.name).ifPresent(s -> {
                throw new RestException(HttpStatus.CONFLICT, "Another sample with the same name already exists in the database");
            });
        }
        sample.setName(request.name);
        sample.setDescription(request.description);
        return this.sampleRepository.save(sample);
    }

    private static class SampleApiRequest {

        @NotBlank
        public String name;
        public String description;

    }

}
