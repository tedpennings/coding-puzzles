# Numeric Diamonds from Code Golf
# 12 August 2013
# http://codegolf.com/numeric-diamonds
require "matrix"

sq = STDIN.read.chomp.to_i
root = (sq**0.5).to_i
w=sq.to_s.length
ll=(root*2-1)

nums = Matrix.build(root, root) {|row, col| (col+1) + (row*root) }

debug = true

puts nums.inspect if debug
puts nums.to_s if debug

1.upto(ll).each do |l|
	x = (l <= sq) ? l : l-sq
	sp = (root-x).abs 
	c = root - sp
	puts "#{sp} #{root-sp} - #{c}" if debug

	case l
	when 1
		puts " " * (sp*w) + "1"   unless debug
	when ll
		puts " " * (sp*w) + sq.to_s  unless debug
	else 
		out=""
		1.upto(c) do |i|
			#out<<("h" + (" "*w))
			a = nums[i-1,i]
			#puts "zzz - #{c} #{l} #{a}"
			out << ( a.to_s + (" "*w))
		end
		puts " " * (sp*w) + out unless debug
	end 
end