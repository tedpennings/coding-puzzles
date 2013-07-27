# Performance
# real	0m0.400s
# user	0m0.386s
# sys	0m0.008s

def power_of_3? x
	raise new ArgumentError if x < 0
	return true if x == 0 or x == 1 # base cases
	return false if x % 3 != 0
	power_of_3? x/3
end

0.upto(1000000) do |x|
	answer = power_of_3? x
	puts "#{x} is a power of three"  if answer
end
