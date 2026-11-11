#!/bin/bash
# bc.sh Class method [n] - compact dex listing
S=${S:?set S to the work folder (dex listings in $S/jadx/dex, rebuild listings in $S/rb)}
bash "$(dirname "$0")/dexm.sh" "$1" "$2" ${3:-1} | grep -v 'nop //\|^\s*$\|move-result' | sed -E 's/^.*\|([0-9a-f]{4}): /\1: /' | cut -c1-150
