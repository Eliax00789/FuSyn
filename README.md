# FuSyn<br>Programming Language
#### IMPORTANT: THIS IS A WIP AND SHOULD/CAN NOT BE USED FOR ANYTHING

(Spoken fu-syn or fusion)

- [Syntax](#syntax)
- [Example Code](#example-code)
- [Available Functions](#available-functions)
- [CLI Arguments](#cli-arguments)

## Syntax

0 Keywords

List Types:
- `()`: Exec function
  - Syntax: `(function-name arg1 ... argN)`
  - Returns the result of the function call
- `[]`: List
  - Syntax: `[value1 ... valueN]`
  - Returns all the given values as a list
- `{}`: AST
  - Syntax: `{anything}`
  - Returns its own code without evaluating any values inside
  - If executed: returns the last value returned by an inner list
- `<>`: Var
  - Syntax:
    - Get: `<var-name>`
    - Set: `<var-name value>`
  - Returns the variable value (also when setting)


## Example Code
```

(iterable::foreach [a b c d] letter {
    (debug <letter>)
})
```
For more examples look into the [src/test/resources/std](src/test/resources/std) folder


## Available functions
- `(debug [Any value]) Any`
- `(if [Bool condition] [AST code]) Any`
- `(if-else [Bool condition] [AST if_branch] [AST else_branch]) Any`
- `(while [AST condition] [AST code]) Any`
- ast
  - `(ast::exec [AST ast]) Any`
  - `(ast::position [AST ast]) Int`
  - `(ast::type [AST ast]) Str`
- bool
  - `(bool::and [Bool a] [Bool b]) Bool`
  - `(bool::false) Bool`
  - `(bool::not [Bool value]) Bool`
  - `(bool::or [Bool a] [Bool b]) Bool`
  - `(bool::short-and [Bool a] [AST b_code]) Bool`
  - `(bool::short-or [Bool a] [AST b_code]) Bool`
  - `(bool::true) Bool`
  - `(bool::xor [Bool a] [Bool b]) Bool`
- file
  - `(file::from-path [Path path]) File`
  - `(file::read-str [File file]) Str`
- func
  - `(func::create [List args] [Str return_type] [AST code]) Func`
- int
  - `(int::add [Int a] [Int b]) Int`
  - `(int::from-str [Str value]) Int`
- iterable
  - `(iterable::foreach [Iterable iterable] [Str variable] [AST code]) Any`
- list
  - `(list::add [List list] [Any value]) List`
  - `(list::first [List list]) Any`
  - `(list::get [List list] [Int index]) Any`
  - `(list::last [List list]) Any`
  - `(list::size [List list]) Int`
- path
  - `(path::from-str [Str path]) Path`
- regex
  - `(regex::from-str [Str value]) Regex`
  - `(regex::match [Str value] [Regex regex]) List`
  - `(regex::split [Str value] [Regex regex]) List`
- str
  - `(str::concat [Str a] [Str b]) Str`
  - `(str::eq [Str a] [Str b]) Bool`
  - `(str::from-int [Int value]) Str`
## CLI Arguments
- Run a file: `-f <file>`
- Repl (may not work correctly): `-i`
