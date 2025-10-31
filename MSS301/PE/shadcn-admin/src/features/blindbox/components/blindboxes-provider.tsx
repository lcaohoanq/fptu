import React, { useState } from 'react'
import useDialogState from '@/hooks/use-dialog-state'
import type { BlindBox } from '../types/blindbox.types'

type UsersDialogType = 'invite' | 'add' | 'edit' | 'delete'

type BlindBoxContextType = {
  open: UsersDialogType | null
  setOpen: (str: UsersDialogType | null) => void
  currentRow: BlindBox | null
  setCurrentRow: React.Dispatch<React.SetStateAction<BlindBox | null>>
}

const BlindBoxContext = React.createContext<BlindBoxContextType | undefined>(
  undefined
)

export function UsersProvider({ children }: { children: React.ReactNode }) {
  const [open, setOpen] = useDialogState<UsersDialogType>(null)
  const [currentRow, setCurrentRow] = useState<BlindBox | null>(null)

  return (
    <BlindBoxContext.Provider
      value={{ open, setOpen, currentRow, setCurrentRow }}
    >
      {children}
    </BlindBoxContext.Provider>
  )
}

// eslint-disable-next-line react-refresh/only-export-components
export const useBlindboxes = () => {
  const blindBoxContext = React.useContext(BlindBoxContext)

  if (!blindBoxContext) {
    throw new Error('useBlindboxes has to be used within <BlindBoxContext>')
  }

  return blindBoxContext
}
