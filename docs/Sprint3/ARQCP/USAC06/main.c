#include <stdio.h>
#include "asm.h"

int main() {
    char input[] = "token1=123.45&token2=678.90";
    char token[] = "token1";
    int output;
    int result = extract_token(input, token, &output);
    printf("Token: %s\n", token);
    printf("Extracted Value: %d\n", output);
    if (result == 1) {
		printf("Success\n");
	}
    return 0;
}
