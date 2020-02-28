#!/bin/bash
dir=$(dirname $(readlink -f "$0"))
cd $dir
args=
for arg in "$@"
do
    args="$args $arg"
done

./myfavorites-1.0.0 &
