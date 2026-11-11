#!/bin/bash
C=$1; M=$2; N=${3:-1}; : "${S:?set S to the work folder}"
cd $S/rb
f=$(grep -l "Class descriptor  : 'Lcom/starpilot/starview/$C;'" classes*.txt | head -1)
s=$(grep -n "Class descriptor  : 'Lcom/starpilot/starview/$C;'" $f | head -1 | cut -d: -f1)
awk -v s=$s -v m="$M" -v n=$N 'NR>s && $0 == "      name          : '"'"'" m "'"'"'" {c++; if (c==n) {p=1}} p {print} p && /^      positions/ {exit}' $f | cut -c50- | sed 's/ \/\/ method@[0-9a-f]*//; s/ \/\/ string@[0-9a-f]*//; s/ \/\/ field@[0-9a-f]*//; s/ \/\/ type@[0-9a-f]*//; s/Lcom\/starpilot\/starview\///g; s/Lai\/comma\/cereal\///g; s/Ljava\/lang\///g; s/Lkotlin\/jvm\/internal\///g'
