#!/bin/bash

printf "\nStarting Testbed.\n\n"

# run-testbed-automation.sh
export has_superpeer_values="false true"
export number_of_peers_values="5 10"
export choice_of_pdf_mb_values="128 64"

# Base directory path where all project-related files are located
export BASE_PATH="$HOME/Desktop"

./configuration-testbed.sh

printf "\nStopping Testbed.\n\n"
