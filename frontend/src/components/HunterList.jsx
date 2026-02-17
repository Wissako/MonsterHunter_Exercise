import { useEffect, useState } from 'react';

export default function HunterList() {
  const [hunters, setHunters] = useState([]);
  const token = localStorage.getItem('jwt_token');

  // Cargar cazadores
  useEffect(() => {
    fetch('http://localhost:8080/hunters')
      .then(res => res.json())
      .then(data => setHunters(data))
      .catch(err => console.error(err));
  }, []);

  // Función para borrar (Requiere Token y ser ADMIN)
  const handleDelete = async (id) => {
    if(!window.confirm("¿Seguro que quieres expulsar a este cazador?")) return;

    try {
        const res = await fetch(`http://localhost:8080/hunters/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}` 
            }
        });

        if(res.ok) {
            alert("Cazador eliminado");
            setHunters(hunters.filter(h => h.id !== id)); // Actualizamos la lista visualmente
        } else {
            alert("Error: No tienes permisos (Solo ADMIN)");
        }
    } catch (error) {
        alert("Error de conexión");
    }
  };

  return (
    <section>
      <h2>🏹 Cazadores del Gremio</h2>
      <div className="overflow-auto">
        <table className="striped">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Rango</th>
                    <th>Arma</th>
                    <th>Acción</th>
                </tr>
            </thead>
            <tbody>
                {hunters.map(h => (
                    <tr key={h.id}>
                        <td>{h.name}</td>
                        <td>{h.rank}</td>
                        <td>{h.mainWeapon}</td>
                        <td>
                            <button 
                                style={{backgroundColor: 'red', border:'none', padding:'5px 10px'}}
                                onClick={() => handleDelete(h.id)}>
                                🗑️
                            </button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
      </div>
    </section>
  );
}