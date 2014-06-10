
Playing with Julia Language

 * http://julialang.org/
 * http://docs.julialang.org/en/release-0.2/manual/

Later:

 * Find and install Emacs mode

Notes:
--------

 * Startup is slow
 * Q: What's "nothing"? 
 * Promotion didn't work for me.
 * Macro needs "@" prefix.

Install pacakges:
-------------------

See: http://docs.julialang.org/en/release-0.2/manual/packages/

    Pkg.add("Stats")

See `~/.julia/v0.2/METADATA/` for the list of available pacakges.
It comes from `git://github.com/JuliaLang/METADATA.jl`

There are "Stats", "DataFrames" and more.

No way to do Bundler-like isolation? I'm not sure.

Do `Pkg.register()` to update `METADATA`, then PR it to publish the library. This is cool.
Github centric.