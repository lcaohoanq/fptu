import { useState } from 'react'
import {
  useUpdateBlindBox,
  useDeleteBlindBox,
} from '@/features/blindbox/hooks/useBlindBoxes'
import type { BlindBox } from '../types/blindbox.types'

type Props = {
  row: { original: BlindBox }
}

export function BlindBoxRowActions({ row }: Props) {
  const blindBox = row.original
  const { mutateAsync: updateAsync } = useUpdateBlindBox()
  const { mutateAsync: deleteAsync } = useDeleteBlindBox()
  const [isWorking, setWorking] = useState(false)

  const onEdit = async () => {
    const newName = window.prompt('Edit blind box name', blindBox.name)
    if (!newName || newName === blindBox.name) return
    try {
      setWorking(true)
      await updateAsync(blindBox.id, { name: newName })
    } finally {
      setWorking(false)
    }
  }

  const onDelete = async () => {
    const ok = window.confirm(`Delete blind box "${blindBox.name}"?`)
    if (!ok) return
    try {
      setWorking(true)
      await deleteAsync(blindBox.id)
    } finally {
      setWorking(false)
    }
  }

  return (
    <div className='flex items-center gap-2'>
      <button onClick={onEdit} disabled={isWorking} className='text-blue-600'>
        Edit
      </button>
      <button onClick={onDelete} disabled={isWorking} className='text-red-600'>
        Delete
      </button>
    </div>
  )
}
