#!/bin/bash
rm -rf wei
rm -rf wei.tar.gz
mkdir -p wei/target
cp -r ./target ./wei
cp wei.service ./wei/
cp scripts/*.sh ./wei
tar -zcvf wei.tar.gz wei
