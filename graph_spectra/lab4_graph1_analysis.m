% Lab 4.
% Code inspired by: 
% Asad Ali
% GIK Institute of Engineering Sciences & Technology, Pakistan
% Email: asad_82@yahoo.com

% clear variables
clear all;
close all;

% read csv graph
data = csvread("../graph_spectra/example1.csv");

% compute A (step 1 in algorithm)
sigma = 1;
for i=1:size(data,1)    
    for j=1:size(data,1)
        dist = sqrt((data(i,1) - data(j,1))^2 + (data(i,2) - data(j,2))^2); %Euclidean distance
        A(i,j) = exp(-dist/(2*sigma^2));
    end
end

% compute D (diagonal matrix)
for i=1:size(A,1)
    D(i,i) = sum(A(i,:));
end

% compute L = D^(-1/2)AD^(-1/2)
for i=1:size(A,1)
    for j=1:size(A,2)
        L(i,j) = A(i,j) / (sqrt(D(i,i)) * sqrt(D(j,j)));  
    end
end

% k = 4 determined after data exploration
k = 4;

% find k largest eigenvectors of L and construct matrix X of those
[eigVectors,eigValues] = eig(L);
nEigVec= eigVectors(:,(size(eigVectors,1)-(k-1)): size(eigVectors,1));

% construct the matrix Y, renormalization of X
for i=1:size(nEigVec,1)
    n = sqrt(sum(nEigVec(i,:).^2));    
    Y(i,:) = nEigVec(i,:) ./ n; 
end

% perform kmeans clustering on Y
[IDX,C] = kmeans(Y,k); 

% plot clusters in different colours
figure,
hold on;
for i=1:size(IDX,1)
    if IDX(i,1) == 1
        plot(data(i,1),data(i,2),'m+');
    elseif IDX(i,1) == 2
        plot(data(i,1),data(i,2),'g+');
    elseif IDX(i,1) == 3 
        plot(data(i,1),data(i,2),'b+');
    else
        plot(data(i,1),data(i,2),'k+');
    end
end
hold off;
title('Clusters after K-Means');
grid on;shg
