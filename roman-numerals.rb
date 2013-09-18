# Parses a Roman Numeral into a standard integer.
# Note: Roman numerals have strict rules for the sequence 
# of characters for subtraction; this parser does not bother
# honoring them, as they only result in more errors and do not
# change the parsed value.
# ::numeral:: the number to parse
def numeral_to_string numeral
  raise ArgumentError, "Invalid characters in numeral '#{numeral}'" unless numeral =~ /^[IVXLCDM]+$/i
  numbers = numeral.upcase.chars.map do |char|
    case char
    when "I"
      1
    when "V"
      5
    when "X"
      10
    when "L"
      50
    when "V"
      50
    when "C"
      100
    when "D"
      500
    when "M"
      1000
    end
  end
  sum = 0
  numbers.each_with_index do |n, i|
    # check for negative values (subtraction) before summing
    n = n * -1 if !numbers[i+1].nil? && numbers[i+1] > n
    sum += n
  end
  sum
end

def print_it numeral
  translated = numeral_to_string numeral
  puts "#{numeral} is #{translated}"
end

["L", "X", "XXIX", "IVXLCDM", "CCXXXIII", "CCCLXXVIII", "XCIX", "MCMXLIV"].each { |i| print_it i }
