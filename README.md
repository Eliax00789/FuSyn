# FuSyn<br>Programming Language

(Spoken fu-syn or fusion)

Everything is a Function

### Example
```
(cf::foreach [a b c d] letter {
    (debug (var::get letter))
})
```
For more examples look into the [src/test/resources](src/test/resources/std) folder

### Running
1. Compile the java code using your favourite build system
2. Run `java -jar fusyn.jar -f code.fsy`
