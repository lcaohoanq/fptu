import type { ColumnDef } from '@tanstack/react-table'
import { Checkbox } from '@/components/ui/checkbox'
import { DataTableColumnHeader } from '@/components/data-table'
import type { BlindBox } from '../types/blindbox.types'
import { BlindBoxRowActions } from './blindbox-row-actions'

export const blindBoxColumns: ColumnDef<BlindBox>[] = [
  {
    id: 'select',
    header: ({ table }) => (
      <Checkbox
        checked={table.getIsAllPageRowsSelected()}
        onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
      />
    ),
    cell: ({ row }) => (
      <Checkbox
        checked={row.getIsSelected()}
        onCheckedChange={(value) => row.toggleSelected(!!value)}
      />
    ),
    enableSorting: false,
  },

  {
    accessorKey: 'name',
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title='Name' />
    ),
  },
  {
    accessorKey: 'rarity',
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title='Rarity' />
    ),
  },
  {
    accessorKey: 'price',
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title='Price' />
    ),
  },
  {
    accessorKey: 'releaseDate',
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title='Release Date' />
    ),
    cell: ({ row }) => {
      const value = row.getValue('releaseDate')
      const date = value ? new Date(value as string) : null

      return <span>{date ? date.toLocaleDateString('en-GB') : '-'}</span>
    },
  },
  {
    accessorKey: 'stock',
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title='Stock' />
    ),
  },
  {
    accessorKey: 'categoryName',
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title='Category Name' />
    ),
  },
  {
    id: 'actions',
    cell: BlindBoxRowActions,
  },
]
