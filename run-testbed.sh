#!/bin/bash
# This is a shebang line that tells the system this script should be run with bash

printf "\nStarting Testbed.\n\n"

# run-testbed-automation.sh
export p2p_algorithm_used_values="true"  # Sets an environment variable indicating if super peers are used
export number_of_peers_values="5"   # Sets an environment variable for the number of peers
export choice_of_pdf_mb_values="2 4 8 16 32 64"  # Sets an environment variable with different PDF file sizes

# Base directory path where all project-related files are located
export BASE_PATH="$HOME/Desktop"  # Sets an environment variable for the base path of project files

./configuration-testbed.sh  # Executes the configuration-testbed.sh script

printf "\nStopping Testbed.\n\n" 
