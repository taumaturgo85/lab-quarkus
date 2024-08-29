#!/bin/bash

# Array com os nomes das subpastas
subfolders=("election-management" "result-app" "voting-app")

# Array com os nomes das pastas a serem criadas dentro de cada subpasta
folders=("api" "domain" "infrastructure")

# Iterar sobre cada subpasta
for subfolder in "${subfolders[@]}"; do
  # Iterar sobre cada pasta a ser criada
  for folder in "${folders[@]}"; do
    # Criar a estrutura de pastas
    mkdir -p "$subfolder/src/main/java/$folder"
  done
done
