export enum UserRole {
  ADMIN = 'ADMIN',
  MODERATOR = 'MODERATOR',
  DEVELOPER = 'DEVELOPER',
  MEMBER = 'MEMBER',
}

export type User = {
  id: number
  username: string
  email: string
  role: UserRole
  active: boolean
}
