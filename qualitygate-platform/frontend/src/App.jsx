import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import ApplicationsListPage from './pages/ApplicationsListPage';
import CreateApplicationPage from './pages/CreateApplicationPage';
import EditApplicationPage from './pages/EditApplicationPage';
import ApplicationDetailsPage from './pages/ApplicationDetailsPage';
import './index.css';

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <main className="main-content">
        <Routes>
          <Route path="/" element={<ApplicationsListPage />} />
          <Route path="/applications/new" element={<CreateApplicationPage />} />
          <Route path="/applications/:id" element={<ApplicationDetailsPage />} />
          <Route path="/applications/:id/edit" element={<EditApplicationPage />} />
        </Routes>
      </main>
    </BrowserRouter>
  );
}

export default App;