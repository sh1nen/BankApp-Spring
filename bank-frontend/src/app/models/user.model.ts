export interface User {
    userId: number,
    username: string,
    password: string,
    firstName: string,
    lastName: string,
    email: string,
    phone: string,
    enabled: boolean,
    primaryAccount: {
        id: number,
        accountNumber:  number,
        accountBalance: number,
    },
    savingsAccount: {
        id: number,
        accountNumber: number,
        accountBalance: number
        },
    authorities: [
        {
            authority: "ROLE_ADMIN"
        }
    ],
    accountNonLocked: boolean,
    accountNonExpired: boolean,
    credentialsNonExpired: boolean
}
