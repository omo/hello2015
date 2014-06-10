ix = 1.0

z = "My String"

# http://docs.julialang.org/en/release-0.2/manual/integers-and-floating-point-numbers/
for T = {Int8,Int16,Int32,Int64,Int128,Uint8,Uint16,Uint32,Uint64,Uint128}
  println("$(lpad(T,6)): [$(typemin(T)),$(typemax(T))]")
end

println(bits(10))
println(bits(10.0))


onepointtwo = 1.1 + 0.1
onepointtworounded = with_rounding(RoundDown) do
  1.1 + 0.1
end

println(onepointtworounded)
println(onepointtwo)

println(BigInt("123456789012345678901234567890") + 1)

rational = 1//2
println(rational, " ", typeof(rational))

# "end" keyword?

str = "Hello"
println(str[end])

# Regular expressions

r = r"^\s*(?:#|$)"
println(typeof(r))
m = ismatch(r, "not a comment")
println("match:$m")

m = match(r"(a|b)(c)?(d)", "ad")
println(m)

first, second, third = m.captures
println(first, ",", second, ",", third)

# Byte array literal
println(typeof(b"DATA\xff\u2200"))

# Functions

function f1(x, y)
  x + y
end

f2(x,y) = x + y

println(f1(3, 4))
println(f2(5, 6))
println(apply(f1, 5, 6))

# Operators are functions
println(+(1, 2))

# anonymous function
println(map(x -> x^x, [1, 2, 3, 4]))

# Multiple return values
function tuplize(x)
  x, x
end

tx = tuplize(3)
println(tx)
println(typeof(tx))

# varargs

function vararghello(a, y, x...)
  println(typeof(x))
  println(x)
end

vararghello(1, 2)
vararghello(1, 2, 3)
vararghello(1, 2, 3, 4)

# Keyword args
function kwfunc(x; foo="bar")
  println(foo)
end

kwfunc(1)
kwfunc(2, foo="BAR")
println(kwfunc)

# Block syntax

sq = map([1, 2, 3]) do x
  x*x
end

println(sq)

println(begin
  1
  2
  3
end)

# Loop and range
for i = 1:3
  println("i:$i")
end

for i = [4,5,6]
  println("i:$i")
end

# Exception

try
  sqrt(-1)
catch e
  println("catch:$e")
finally
  println("finally.")
end

function producer()
  produce("start")
  for n=1:4
    produce(2n)
  end
  produce("stop")
end

p = Task(producer)

while true
  c = consume(p)
  println(c)
  if c == "stop"
    break
  end
end

# This works How iteration is modeled?
for i = Task(producer)
  println(i)
end

funcs = map([1,2,3]) do i
  let value = i*2
    () -> println("Value is: $i, $value")
  end
end

for f = funcs; f(); end

# Types

type Foo
  bar
  baz::Int
  qux::Float64
end

foo = Foo(1, 2, 3.0)
println(foo, " ", typeof(foo))

immutable type ImmFoo
  bar
  baz::Int
  qux::Float64
end

println(ImmFoo(1, 2, 3.0))

IntOrString = Union(Int, String)

# ios::IntOrString # Doesn't work. Why?
isstring = "Hello"::IntOrString
isint = 1::IntOrString

abstract Pointy{T}

type Point{T <: Real} <: Pointy{T}
  x::T
  y::T
end

println(typeof(Point(1,2)))
println(Point{Int64} <: Point)

println(isa(1, Integer))

inttype = Int32
while inttype != Any
  println(inttype)
  inttype = super(inttype)
end

#println(methods(+))
methods(+)

type OrderedPair
  x::Real
  y::Real

  # Inner constructor
  OrderedPair(x,y) = x > y ? error("out of order") : new(x,y)
end

# Outer constructor
OrderedPair() = OrderedPair(0, 0)

op0 = OrderedPair()
println(op0)

op1 = OrderedPair(1, 2)
println(op1)

try
  op2 =  OrderedPair(2, 1)
  println(op2)
catch e
  println(e)
end

type ParametricP{T<:Real}
  x::T
  y::T
end

println(ParametricP(1, 2))
println(ParametricP{Float64}(1.0, 2.0))

# Type promotion - Doesn't work. Why?
# Seems like buggy or error prone. https://github.com/JuliaLang/julia/issues/3571
# Even though the promotion concept seems one of the core of this language.
#  http://docs.julialang.org/en/release-0.2/manual/conversion-and-promotion/
#ParametricT(x::Real, y::Real) = ParametricT(promote(x, y)...)
#println(ParametricT(3, 4.0))

module MyModule
modVar = 123
end

println(MyModule.modVar)

# Metaprogramming

exp = :(1 + (2 * Rational(3, 4)))
println(exp, typeof(exp))
println(eval(exp))

sym = :x
println(sym, typeof(sym))

for op = (:+, :*, :&, :|, :$)
  #eval(:(($op)(a,b,c) = ($op)(($op)(a,b),c)))
  @eval ($op)(a,b,c) = ($op)(($op)(a,b),c) 
end

macro printvalnexp(ex)
  :(println($(string(ex)), "->", $ex))
end

@printvalnexp 1 + 2 + 3

macro zerox()
  esc(:(x = 0))
end

function usezerox()
  x = 1
  @zerox
  println(x)  # is zero
end

usezerox()

# List comprehension
const x = rand(8)
noise = [ 0.25*x[i-1] + 0.5*x[i] + 0.25*x[i+1] for i=2:length(x)-1 ]
println(noise)

# broadcast
a = rand(2,1)
b = rand(1,2)
c = broadcast(+, a, b)
println(c)
println(typeof(c))

# IO

write(STDOUT, "Hello")


cmd = `echo hello`
println(run(cmd), " ", typeof(cmd))

# Command pipelining
run(`echo world` & `echo hello` |> `sort -r`)
