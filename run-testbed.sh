#!/bin/bash

printf "\nStarting Testbed.\n\n"

# run-testbed-automation.sh
export has_superpeer_values="true"
export number_of_peers_values="35"
export choice_of_pdf_mb_values="32 64 128"

# Base directory path where all project-related files are located
export BASE_PATH="$HOME/Desktop"

./configuration-testbed.sh

printf "\nStopping Testbed.\n\n"
