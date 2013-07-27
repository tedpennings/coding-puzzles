# Performance (time)
# real	0m17.562s
# user	0m17.007s
# sys	0m0.041s

def has_cube_root? x
	raise new ArgumentError if x < 0
	y = 0
	while y**3 <= x
		return [true, y] if y**3 == x
		y += 1
	end 
	return false
end

0.upto(1000000) do |x|
	answer = has_cube_root? x
	puts "#{x} has a cube root %s" % answer[1] if answer
end

