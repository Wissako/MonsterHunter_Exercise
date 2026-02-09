import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

export default function HunterList() {
  const [hunters, setHunters] = useState([]);

  // 1. Cargar cazadores (READ)
  const loadHunters = () => {
    fetch('http://localhost:8080/hunters')
      .then(res => res.json())
      .then(setHunters)
      .catch(console.error);
  };

  useEffect(() => {
    loadHunters();
  }, []);

  // 2. Borrar cazador (DELETE)
  const handleDelete = async (id) => {
    if (window.confirm('¿Seguro que quieres retirar a este cazador?')) {
      try {
        await fetch(`http://localhost:8080/hunters/${id}`, { method: 'DELETE' });
        loadHunters(); // Recargar la lista
      } catch (error) {
        alert('Error al borrar');
      }
    }
  };

  return (
    <section>
      <h2>⚔️ Gremio de Cazadores</h2>
      <Link to="/hunters/new" role="button" className="contrast">➕ Nuevo Cazador</Link>
      
      <figure>
        <table role="grid">
          <thead>
            <tr>
              <th>Nombre</th>
              <th>Rango</th>
              <th>Arma</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {hunters.map(h => (
              <tr key={h.id}>
                <td>{h.name}</td>
                <td>{h.rank}</td>
                <td>{h.mainWeapon}</td>
                <td>
                  {/* Botón de Borrar */}
                  <button 
                    className="secondary outline" 
                    onClick={() => handleDelete(h.id)}
                    style={{ marginLeft: '10px', borderColor: 'red', color: 'red' }}
                  >
                    🗑️
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </figure>
    </section>
  );
}