function [output] = f_rgbHist(I,n)
% f_rgbHist n-bin RGB histogram
%
%   Input: I - RGB image
%          n - number of bins
%   Remarks:
%     1. The output histogram is normalized so that the bin with maximum
%     frequency always has value of 1.0. The normalization is done across 
%     colors i.e. [RH GH BH]/max([RH GH BH])
%     

% returns the name of the features when I is empty
if isempty(I)
    Cnames = {'R','G','B'};
    output.header = {};
    output.type = {};
    for j=1:length(Cnames)
        for i=1:n
            fname = [Cnames{j} 'H_' num2str(n) '_' num2str(i)];
            output.header = [output.header fname];
            output.type = [output.type 'float'];
        end
    end
    return;
end

if (ndims(I) == 3 && size(I,3) == 3)
    % center of each bin
    center = linspace(0,255,n+1);
    center = (center(2:end) + center(1:end-1))/2;
    
    % R
    y = double(I(:,:,1));
    y = y(:);
    RH = hist(y,center);
    
    % G
    y = double(I(:,:,2));
    y = y(:);
    GH = hist(y,center);
    
    % B
    y = double(I(:,:,3));
    y = y(:);
    BH = hist(y,center);
    
    Z = max([RH GH BH]);
    output = [RH GH BH]/Z;
else
    output = zeros(1,3*n);
end

end
