task = @async begin
  server = listen(2000)
  while true
    sock = accept(server)
    println("Hello World\n")
  end
end

# XXX: How to wait indefinitely
sleep(1000)
