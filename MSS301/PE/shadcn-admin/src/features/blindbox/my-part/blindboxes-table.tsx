import { useEffect, useState } from 'react'
import {
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  useReactTable,
  type SortingState,
} from '@tanstack/react-table'
import { useTableUrlState, type NavigateFn } from '@/hooks/use-table-url-state'
import { DataTablePagination, DataTableToolbar } from '@/components/data-table'
import { DataTableBulkActions } from '../components/data-table-bulk-actions'
import type { BlindBox } from '../types/blindbox.types'
import { blindBoxColumns } from './column'

type DataTableProps = {
  data: BlindBox[]
  search: Record<string, unknown>
  navigate: NavigateFn
}

export function BlindBoxTable({ data, search, navigate }: DataTableProps) {
  const [rowSelection, setRowSelection] = useState({})
  const [columnVisibility, setColumnVisibility] = useState({})
  const [sorting, setSorting] = useState<SortingState>([])

  const {
    columnFilters,
    onColumnFiltersChange,
    pagination,
    onPaginationChange,
    ensurePageInRange,
  } = useTableUrlState({
    search,
    navigate,
    pagination: { defaultPage: 1, defaultPageSize: 10 },
    globalFilter: { enabled: false },
    columnFilters: [
      {
        columnId: 'name',
        searchKey: 'name',
        type: 'string',
      },
      { columnId: 'rarity', searchKey: 'rarity', type: 'string' },
      { columnId: 'price', searchKey: 'price', type: 'string' },
      { columnId: 'stock', searchKey: 'stock', type: 'string' },
      { columnId: 'categoryName', searchKey: 'categoryName', type: 'string' },
    ],
  })

  // eslint-disable-next-line react-hooks/incompatible-library
  const table = useReactTable({
    data,
    columns: blindBoxColumns,
    state: {
      sorting,
      pagination,
      rowSelection,
      columnFilters,
      columnVisibility,
    },
    onPaginationChange,
    onColumnFiltersChange,
    onRowSelectionChange: setRowSelection,
    onSortingChange: setSorting,
    onColumnVisibilityChange: setColumnVisibility,
    enableRowSelection: true,
    getPaginationRowModel: getPaginationRowModel(),
    getCoreRowModel: getCoreRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    getSortedRowModel: getSortedRowModel(),
  })

  useEffect(() => ensurePageInRange(table.getPageCount()), [table])

  return (
    <>
      <DataTableToolbar
        table={table}
        searchKey='name'
        searchPlaceholder='Search blind boxes...'
        filters={[]}
        /* You can add other filters here later, for example:
           filters={[
             {
               columnId: 'rarity',
               title: 'Rarity',
               options: [
                 { label: 'Common', value: 'common' },
                 { label: 'Rare', value: 'rare' },
               ]
             }
           ]}
        */
      />

      <div className='rounded-md border'>
        <table className='divide-border min-w-full divide-y'>
          <thead className='bg-muted/50'>
            {table.getHeaderGroups().map((headerGroup) => (
              <tr key={headerGroup.id} className='border-border border-b'>
                {headerGroup.headers.map((header) => {
                  return (
                    <th
                      key={header.id}
                      className='text-muted-foreground h-12 px-4 text-left align-middle font-medium'
                    >
                      {header.isPlaceholder
                        ? null
                        : flexRender(
                            header.column.columnDef.header,
                            header.getContext()
                          )}
                    </th>
                  )
                })}
              </tr>
            ))}
          </thead>
          <tbody className='divide-border divide-y'>
            {table.getRowModel().rows?.length ? (
              table.getRowModel().rows.map((row) => (
                <tr
                  key={row.id}
                  data-state={row.getIsSelected() && 'selected'}
                  className='hover:bg-muted/50'
                >
                  {row.getVisibleCells().map((cell) => (
                    <td key={cell.id} className='p-4 align-middle'>
                      {flexRender(
                        cell.column.columnDef.cell,
                        cell.getContext()
                      )}
                    </td>
                  ))}
                </tr>
              ))
            ) : (
              <tr>
                <td
                  colSpan={blindBoxColumns.length}
                  className='h-24 text-center'
                >
                  No results.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      <DataTablePagination table={table} />
      <DataTableBulkActions table={table} />
    </>
  )
}
