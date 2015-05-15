
#[derive(Clone, Copy, Debug)]
enum Sign { Add(char), Sub(char), Nop }

const SUB : Sign = Sign::Sub('-');
const ADD : Sign = Sign::Add('+');
const NOP : Sign = Sign::Nop;

#[derive(Clone, Copy, Debug)]
struct Node { sign : Sign, digit : i64 }

impl Node {
    pub fn new(s : Sign, d : i64) -> Node { Node{ sign: s, digit: d } }
}

fn sum(nodes: &Vec<Node>) -> i64 {
    nodes.iter().chain([Node::new(ADD, 0)].iter()).fold((0i64, Node::new(NOP, 0)), |a, i| {
        match i.sign {
            Sign::Add(_) | Sign::Sub(_) => {
                match a.1.sign {
                    Sign::Add(_) | Sign::Nop => (a.0 + a.1.digit, *i),
                    Sign::Sub(_)             => (a.0 - a.1.digit, *i)
                }
            }
            Sign::Nop    => (a.0, Node::new(a.1.sign, a.1.digit*10 + i.digit))
        }
    }).0
}

#[test]
fn sum_works() {
    assert_eq!( 6, sum(&vec![Node::new(NOP, 1), Node::new(ADD, 2),Node::new(ADD, 3)]));
    assert_eq!( 2, sum(&vec![Node::new(NOP, 1), Node::new(SUB, 2),Node::new(ADD, 3)]));
    assert_eq!(24, sum(&vec![Node::new(NOP, 1), Node::new(ADD, 2),Node::new(NOP, 3)]));
}

fn stringify(nodes: &Vec<Node>) -> String {
    nodes.iter().fold(String::new(), |s, i| {
        // XXX: Wanting use as_str() but it is still unstable!!
        format!("{}{}",
                s,
                match i.sign {
                    Sign::Add(ch) => format!("{}{}", ch, i.digit),
                    Sign::Sub(ch) => format!("{}{}", ch, i.digit),
                    Sign::Nop     => format!("{}", i.digit)
                })
    })
}

#[test]
fn strintify_works() {
    assert_eq!("123".to_string(),
               stringify(&vec![Node::new(NOP, 1), Node::new(NOP, 2),Node::new(NOP, 3)]));
    assert_eq!("1-2+3".to_string(),
               stringify(&vec![Node::new(NOP, 1), Node::new(SUB, 2),Node::new(ADD, 3)]));
}

fn enumerate() {
    let mut nodes: Vec<Node> = vec![Node::new(NOP, 1), Node::new(NOP, 2)];
    while 1 < nodes.len() {
        if nodes.len() < 9 {
            let n = *nodes.last().unwrap();
            nodes.push(Node::new(NOP, n.digit + 1));
            continue;
        }

        // Visit.
        if sum(&nodes) == 100 {
            println!("{}", stringify(&nodes));
        }

        while !nodes.is_empty() && {
            let n = nodes.pop().unwrap();
            match n.sign {
                Sign::Nop => { nodes.push(Node::new(ADD, n.digit)); false }
                Sign::Add(_) => { nodes.push(Node::new(SUB, n.digit)); false }
                Sign::Sub(_) => { true }
            }
        } { }
    }
}

pub fn main() {
    enumerate();
}
