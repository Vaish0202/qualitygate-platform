import { Link } from 'react-router-dom';

function Navbar() {
  return (
    <nav className="navbar">
      <div className="navbar-brand">QualityGate</div>
      <div className="navbar-links">
        <Link to="/">Applications</Link>
      </div>
    </nav>
  );
}

export default Navbar;