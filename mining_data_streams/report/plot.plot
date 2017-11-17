set term postscript eps enhanced color
set output 'data4.eps'
set style fill solid border rgb "black"

set boxwidth 0.8
set style data histograms

set xlabel "Reservoir sample size"
set autoscale
set ylabel "Number of triangles"
set title "Benchmarks"
set offset -0.3,-0.3,30,0.0
set yrange [0:50];
plot for [COL=2:3] 'data3.dat' using COL:xticlabels(1) title columnheader
