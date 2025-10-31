import z from 'zod'
import { createFileRoute } from '@tanstack/react-router'
import { BlindBoxes } from '@/features/blindbox'

const blindboxesSearchSchema = z.object({
  page: z.number().optional().catch(1),
  pageSize: z.number().optional().catch(10),
  // Facet filters (status used by blindboxes table)
  status: z
    .array(z.union([z.literal('active'), z.literal('draft')]))
    .optional()
    .catch([]),
  // Per-column text filter for name
  name: z.string().optional().catch(''),
})

export const Route = createFileRoute('/_authenticated/blindboxes/')({
  validateSearch: blindboxesSearchSchema,
  component: BlindBoxes,
})
