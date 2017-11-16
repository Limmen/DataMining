set term postscript eps enhanced color
set output 'data4.eps'
set style fill solid border rgb "black"

set boxwidth 0.8
set style data histograms

set xlabel "k"
set autoscale
set ylabel "amount (log scale)"
set logscale y 2
set title "Benchmarks"
set offset -0.3,-0.3,30,0.0
plot for [COL=2:5] 'data3.dat' using COL:xticlabels(1) title columnheader
