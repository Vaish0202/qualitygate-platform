import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import ApplicationsListPage from './pages/ApplicationsListPage';
import './index.css';

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <main className="main-content">
        <Routes>
          <Route path="/" element={<ApplicationsListPage />} />
        </Routes>
      </main>
    </BrowserRouter>
  );
}

export default App;