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
  - Returns itself - May be replaced by a simple list in the future
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
- `(ast::position AST) Int`
- `(ast::type AST) Str`
- `(bool::and Bool Bool) Bool`
- `(bool::false) Bool`
- `(bool::not Bool) Bool`
- `(bool::or Bool Bool) Bool`
- `(bool::short-and Bool AST) Bool`
- `(bool::short-or Bool AST) Bool`
- `(bool::true) Bool`
- `(bool::xor Bool Bool) Bool`
- `(c List) Null`
- `(cf::if Bool AST) Any`
- `(cf::if-else Bool AST AST) Any`
- `(cf::while AST AST) Any`
- `(debug Any) Any`
- `(func::call Func List) Any`
- `(func::get Str) Func`
- `(func::set Str List Str AST) Func`
- `(list::add List Any) List`
- `(list::foreach List Str AST) Any`
- `(list::get List Int) Any`
- `(list::size List) Int`


## CLI Arguments
- Run a file: `-f <file>`
- Repl (may not work correctly): `-i`
