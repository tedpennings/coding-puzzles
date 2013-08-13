# Saving Time from Code Golf, http://codegolf.com/saving-time
# 12 August 2013

# Problem: given a 24hr time, produce a clock like the below
#        o
#    o       o
#
# o             o
#
#h               o
#
# o             o
#
#    m       o
#        o
# Note that it must be faithful to the above, character for character.

# This solution is not optimized for LOC

time = STDIN.read.chomp
hour, minutes = time.split(/\:/)

hour_dot = hour.to_i >= 12 ? hour.to_i - 12 : hour.to_i
minute_dot = minutes.to_i / 5

hands = Array.new

if hour_dot == minute_dot
	(0..12).each { |i| hands[i] = "o" }
	hands[hour_dot] = "x"
else
	(0..12).each do |i|
		case i
		when hour_dot
			hands[i] = "h"
		when minute_dot
			hands[i] = "m"
		else
			hands[i] = "o"
		end
	end
end

0.upto(12) do |l|
	case l
	when 3,5,7,9
		puts #empty lines
	when 0
		puts " "*8 + hands[0]
	when 1
		puts " " *4 + hands[11] + " "* 7 + hands[1]
	when 4
		puts " " + hands[10] + " " * 13 + hands[2]
	when 6
		puts hands[9] + " " * 15 + hands[3]
	when 8
		puts " " + hands[8] + " " * 13 + hands[4]
	when 10
		puts " " *4 + hands[7] + " "* 7 + hands[5]
	when 12
		puts " "*8 + hands[6]
	end
end