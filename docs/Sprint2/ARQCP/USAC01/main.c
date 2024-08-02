#include <stdio.h>
#include "asm.h"

int main() {
    char input[] = "token1=123.45&token2=678.90";
    char token[] = "token1";
    int output;
    extract_token(input, token, &output);
    printf("Token: %s\n", token);
    printf("Extracted Value: %d\n", output);
    return 0;
}
