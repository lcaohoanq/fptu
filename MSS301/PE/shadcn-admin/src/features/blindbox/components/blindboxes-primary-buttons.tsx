import { UserPlus } from 'lucide-react'
import { Button } from '@/components/ui/button'
import { useBlindboxes } from './blindboxes-provider'

export function BlindBoxesPrimaryButtons() {
  const { setOpen } = useBlindboxes()
  return (
    <div className='flex gap-2'>
      <Button className='space-x-1' onClick={() => setOpen('add')}>
        <span>Add Blind Box</span> <UserPlus size={18} />
      </Button>
    </div>
  )
}
