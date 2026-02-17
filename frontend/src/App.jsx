import { useState } from 'react';
import MonsterList from './components/MonsterList';
import QuestList from './components/QuestList';
import HunterList from './components/HunterList';
import MonsterForm from './components/MonsterForm';
import QuestForm from './components/QuestForm';
import Login from './components/Login';
import Register from './components/Register';

export default function App() {
  const [token, setToken] = useState(localStorage.getItem('jwt_token'));
  const [currentView, setCurrentView] = useState('monsters');

  const logout = () => {
    localStorage.removeItem('jwt_token');
    setToken(null);
  };

  if (!token) {
    return (
        <div className="container" style={{ marginTop: '2rem' }}>
            <header style={{ textAlign: 'center', marginBottom: '2rem' }}>
                <h1>⚔️ Gremio Monster Hunter</h1>
                <p>Identifícate para acceder a los registros</p>
            </header>
            <div className="grid">
                <Login onLogin={() => setToken(localStorage.getItem('jwt_token'))} />
                <Register />
            </div>
        </div>
    );
  }

  return (
    <div className="container">
      <nav>
        <ul>
            <li><strong>🐉 Gremio MH</strong></li>
        </ul>
        <ul>
            <li>
                <button 
                    className={currentView === 'monsters' ? '' : 'outline'} 
                    onClick={() => setCurrentView('monsters')}>
                    🦖 Monsters
                </button>
            </li>
            <li>
                <button 
                    className={currentView === 'quests' ? '' : 'outline'} 
                    onClick={() => setCurrentView('quests')}>
                    📜 Quests
                </button>
            </li>
            <li>
                <button 
                    className={currentView === 'gestion' ? 'contrast' : 'outline contrast'} 
                    onClick={() => setCurrentView('gestion')}>
                    ⚙️ Gestión
                </button>
            </li>
        </ul>
        <ul>
            <li><button onClick={logout} className="outline secondary" style={{border: 'none'}}>Salir</button></li>
        </ul>
      </nav>

      <hr />

      <main>
        {currentView === 'monsters' && (
            <div>
                <h2 style={{textAlign:'center'}}>📖 Bestiario del Gremio</h2>
                <MonsterList />
            </div>
        )}

        {currentView === 'quests' && (
            <div>
                <h2 style={{textAlign:'center'}}>🛡️ Tablón de Misiones</h2>
                <QuestList />
            </div>
        )}

        {currentView === 'gestion' && (
            <div className="grid">
                <div>
                    <h3>🛠️ Crear Nuevo Contenido</h3>
                    <MonsterForm />
                    <br />
                    <QuestForm />
                </div>
                <div>
                    <h3>👥 Censo de Cazadores</h3>
                    <HunterList />
                </div>
            </div>
        )}
      </main>
    </div>
  );
}