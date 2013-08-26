# Parses a Roman Numeral into a standard integer.
# Note: Roman numerals have strict rules for the sequence 
# of characters for subtraction; this parser does not bother
# honoring them, as they only result in more errors and do not
# change the parsed value.
# ::numeral:: the number to parse
def numeral_to_string numeral
  raise ArgumentError, "Invalid characters in numeral '#{numeral}'" unless numeral =~ /^[IVXLCDM]+$/i
  numbers = []
  numeral.upcase.chars.each do |char|
    case char
    when "I"
      numbers << 1
    when "V"
      numbers << 5
    when "X"
      numbers << 10
    when "L"
      numbers << 50
    when "V"
      numbers << 50
    when "C"
      numbers << 100
    when "D"
      numbers << 500
    when "M"
      numbers << 1000
    else #this check is pretty much unnecessary
      raise ArgumentError, "Unable to parse #{char} in #{numeral}"
    end
  end
  sum = 0
  numbers.each_with_index do |n, i|
    n = n * -1 if !numbers[i+1].nil? && numbers[i+1] > n
    sum += n
  end
  sum
end

def print_it numeral
  translated = numeral_to_string numeral
  puts "i is #{translated}"
end

["L", "X", "XXIX", "IVXLCDM", "CCXXXIII", "CCCLXXVIII", "XCIX"].each { |i| print_it i }
