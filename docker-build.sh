#!/bin/bash
set -e

docker buildx build -t db-spring-template:latest .
