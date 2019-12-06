#!/usr/bin/env ruby
class Wire
  def initialize(name, path)
    @name=name
    @path=trace_path(path)
  end
  def trace_path(p)
    points = []
    lines = []
    x = 0
    y = 0
    p.each do |instruction|
      direction = 0
      if instruction[0] == 'U'
        direction = 2
      elsif instruction[0] == 'D'
        direction = -2
      elsif instruction[0] == 'R'
        direction = 1
      elsif instruction[0] == 'L'
        direction = -1
      end
      if direction % 2 == 0
        y += (direction / 2) * instruction[1..-1].to_i
      else
        x += (direction) * instruction[1..-1].to_i
      end
      points[points.length] = [x,y]
    end
    (0..(points.length-1)).each do |i|
      line = []
      if points[i + 1]
        if points[i][0] == points[i + 1][0]
          if points[i][1] < points[i + 1][1]
            line = [points[i], points[i + 1]]
          else
            line = [points[i + 1], points[i]]
          end
        else
          if points[i][0] < points[i + 1][0]
            line = [points[i], points[i + 1]]
          else
            line = [points[i + 1], points[i]]
          end
        end
        lines[lines.length] = line
      end
    end
    lines
  end
  def get_path
    @path
  end
end

def get_path(line)
  line.split(',')
end

def find_closest_intersection(wire1, wire2)
  matches = Array.new
  lines1 = wire1.get_path
  lines2 = wire2.get_path
  lines1.each do |line1|
    lines2.each do |line2|
      # puts [line1, line2].inspect
      if find_intersection(line1, line2)
        matches[matches.length] = find_intersection(line1, line2)
      end
    end
  end
  puts matches.inspect
  if matches.length > 0
    distance = manhattan(matches[0])
    matches.each do |match|
      distance = [distance, manhattan(match)].min
    end
    distance
  else
    nil
  end
end

def find_intersection(line1, line2)
  intersection = nil
  if is_vertical(line1)
    unless is_vertical(line2)
      if line2[0][0] < line1[0][0] and line1[0][0] < line2[1][0]
        if line1[0][1] < line2[0][1] and line2[0][1] < line1[1][1]
          intersection = [line1[0][0], line2[0][1]]
        end
      end
    end
  else
    if is_vertical(line2)
      if line2[0][1] < line1[0][1] and line1[0][1] < line2[1][1]
        if line1[0][0] < line2[0][0] and line2[0][0] < line1[1][0]
          intersection = [line2[0][0], line1[0][1]]
        end
      end
    end
  end
  intersection
end

def is_vertical(line)
  if line[0][0] == line[1][0]
    true
  else
    false
  end
end

def manhattan(coords)
  x = 0 + coords[0]
  y = 0 + coords[1]
  manh = x.abs + y.abs
  manh
end

def do_calculation
  data = IO.readlines("/Users/stygg/Documents/advent2019/data/day3.txt")
  wire1 = Wire.new('wire1', get_path(data[0]))
  wire2 = Wire.new('wire2', get_path(data[1]))
  find_closest_intersection(wire1, wire2)
end

if __FILE__ == $0
  puts do_calculation
end
