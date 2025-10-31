import { getRouteApi } from '@tanstack/react-router'
import { ConfigDrawer } from '@/components/config-drawer'
import { Header } from '@/components/layout/header'
import { Main } from '@/components/layout/main'
import { ProfileDropdown } from '@/components/profile-dropdown'
import { Search } from '@/components/search'
import { ThemeSwitch } from '@/components/theme-switch'
import { BlindBoxesPrimaryButtons } from './components/blindboxes-primary-buttons'
import { UsersProvider } from './components/blindboxes-provider'
import { useBlindBoxes } from './hooks/useBlindBoxes'
import { BlindBoxTable } from './my-part/blindboxes-table'

const route = getRouteApi('/_authenticated/blindboxes/')

export function BlindBoxes() {
  const search = route.useSearch()
  const navigate = route.useNavigate()
  const { data, isLoading, error } = useBlindBoxes()

  if (isLoading) return <div>Loading...</div>
  if (error) return <div>Error loading blind boxes</div>

  return (
    <UsersProvider>
      <Header fixed>
        <Search />
        <div className='ms-auto flex items-center space-x-4'>
          <ThemeSwitch />
          <ConfigDrawer />
          <ProfileDropdown />
        </div>
      </Header>

      <Main className='flex flex-1 flex-col gap-4 sm:gap-6'>
        <div className='flex flex-wrap items-end justify-between gap-2'>
          <div>
            <h2 className='text-2xl font-bold tracking-tight'>BlindBox List</h2>
            <p className='text-muted-foreground'>Manage your blind box here.</p>
          </div>
          <BlindBoxesPrimaryButtons />
        </div>

        <BlindBoxTable data={data ?? []} search={search} navigate={navigate} />
      </Main>
    </UsersProvider>
  )
}
