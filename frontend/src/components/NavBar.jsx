import {Link} from 'react-router-dom';
export default function NavBar() {
    return(
        <nav className='navbar'>
            <ul>
                <li><strong>🐉GREMIO MONSTER HUNTER🐉</strong></li>
            </ul>
            <ul>
                <li><Link to="/monsters" role="button" className="outline">Bestiario</Link></li>
                <li><Link to="/quests" role="button" className="outline">Misiones</Link></li>
                <li><Link to="/hunters/new" role="button" className="outline">Nuevo Cazador</Link></li>
            </ul>
        </nav>
    )
}