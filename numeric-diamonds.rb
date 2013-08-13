# Numeric Diamonds from Code Golf
# 12 August 2013
# http://codegolf.com/numeric-diamonds
def diamond(sq)
	root = (sq**0.5).to_i
	w=sq.to_s.length
	ll=(root*2-1)

	1.upto(ll) do |l|
		x = (l <= sq) ? l : l-sq
		sp = (root-x).abs 
		c = root - sp

		case l
		when 1
			puts " " * (sp*w) + "1"
		when ll
			puts " " * (sp*w) + sq.to_s
		else 
			out=""
			1.upto(c) do |i|
				if l <= root
					num = ((((l-1)*root) + 1) - ((i-1) * (root-1)))
				else
					num = sq - (ll-l) - ((i-1) * (root-1))
				end
				num = num.to_s
				out<<(" " * ((w - num.length))) unless i == c or i == 1
				out<<num
				out<<(" "*w) unless i == c
			end
			puts " " * (sp*w) + out
		end 
	end
end
diamond 9
diamond 36
diamond 100