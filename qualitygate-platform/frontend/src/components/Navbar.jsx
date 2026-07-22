import { Link } from 'react-router-dom';

function Navbar() {
  return (
    <nav className="navbar">
      <Link to="/" className="navbar-brand">QualityGate</Link>
      <div className="navbar-links">
        <Link to="/">Applications</Link>
        <Link to="/applications/new">New Application</Link>
      </div>
    </nav>
  );
}

export default Navbar;