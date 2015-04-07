
mod mymod {
    pub fn hello() {
        println!("Hello, Module!");
    }
}

struct Point {
    x: i32,
    y: i32,
}

fn point_example() {
    let p = Point { x: 1, y: 2 };
    //println!("{}", p);
    println!("{}, {}", p.x, p.y);
}

fn print_number(n: i32) {
    println!("{}", n);
}

fn print_sum(x: i32, y: i32) {
    println!("{}", x + y);
}

fn if_example () {
    let x = 5;
    let y = if x == 5 {
        // No semicolon needed as this is part of sorrounding expression.
        "x is five!"
    } else {
        " x is not filve :("
    };

    println!("I found {}", y);
}

fn add_one(n: i32) -> i32 {
    n + 1
}

fn tuple_example() {
    let x: (i32, &str) = (1, "Hello");
    //println!("{}", x);
    let (x1, x2) = x;
    println!("{}, {}", x1, x2);
}

struct Inches(i32);

fn tuple_record_example() {
    let inches = Inches(10);
    let Inches(ten) = inches;
    println!("ten: {}", ten);
}

enum Character {
    Digit(i32),
    Other,
}

fn enum_print(ch: Character) {
    match ch {
        Character::Digit(n) => println!("Digit {}.", n),
        Character::Other => println!("Non digit."),
    }
}

fn enum_example() {
    let ten = Character::Digit(10);
    let not_ten = Character::Other;
    enum_print(ten);
    enum_print(not_ten);
}

fn loop_example() {
    for i in 0..10 {
        println!("{}", i);
    }
}

fn print_string(s: &str) {
    println!("{}", s);
}

fn string_example() {
    let hello_slice = "Hello";
    let mut hello_obj = hello_slice.to_string();
    print_string(&hello_obj);
    hello_obj.push_str(", World");
    print_string(&hello_obj);
}

fn array_example() {
    let arr = [1, 2, 3];
    for i in arr.iter() {
        println!("{}", i);
    }
}

fn vector_example() {
    let mut v = vec![1, 2, 3];
    v.push(4);
    for i in v.iter() {
        println!("{}", i);
    }
}

#[test]
fn it_works() {
    assert!(true);
}

fn reference_example() {
    let x = 123;
    let y = &x;
    println!("{:p}", y);
    println!("{}", *y);
}

fn box_example() {
    let x = Box::new(5);
    println!("{}", *x);
}

fn collect_example() {
    //let one_to_one_hundred = (1..101).collect::<Vec<i32>>();
    let one_to_one_hundred = (1..101).collect::<Vec<_>>();
    println!("{:?}", one_to_one_hundred);
}

fn find_example() {
    match (1..100).find(|x| *x > 42) {
        Some(x) => println!("Found {}", x),
        None => println!("Nothing found"),
    }
}

pub fn main() {
    let x = 1;
    let (y, z) = (2, 3);
    let w: i32 = 5;
    let mut v = 6;
    v = v + 7;
    println!("Hello, World! {}", v + w + x + y + z);

    mymod::hello();
    print_number(10);
    print_sum(10, 20);
    if_example();
    print_number(add_one(20));

    point_example();
    tuple_example();
    tuple_record_example();
    enum_example();
    loop_example();
    string_example();
    array_example();
    vector_example();
    reference_example();
    box_example();
    collect_example();
    find_example();
}
