import AppRouter from './router/AppRouter'
import { AuthProvider } from './router/AuthContext'

function App() {
  return (
    <AuthProvider>
      <AppRouter />
    </AuthProvider>
  )
}

export default App
