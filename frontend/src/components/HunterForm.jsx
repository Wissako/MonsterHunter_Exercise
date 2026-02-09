import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function HunterForm() {
  const navigate = useNavigate();
  const [weapons, setWeapons] = useState([]);
  
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    rank: 1,
    mainWeapon: ''
  });

  useEffect(() => {
    fetch('http://localhost:8080/hunters/weapons')
      .then(res => res.json())
      .then(data => setWeapons(data))
      .catch(console.error);
  }, []);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/hunters', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData)
      });

      if (response.ok) {
        alert('¡Cazador registrado con éxito!');
        navigate('/'); 
      } else {
        // Ahora sí funciona el await porque la función es async
        const error = await response.json();
        alert('Error: ' + error.message);
      }
    } catch (err) {
      alert('Error de conexión');
    }
  };

  return (
    <article>
      <header><h2>📝 Nuevo Cazador</h2></header>
      <form onSubmit={handleSubmit}>
        <label>Nombre <input type="text" name="name" required onChange={handleChange} /></label>
        <label>Email <input type="email" name="email" required onChange={handleChange} /></label>
        <label>Rango <input type="number" name="rank" min="1" required onChange={handleChange} value={formData.rank} /></label>
        
        <label>
          Arma Principal
          <select name="mainWeapon" required onChange={handleChange} defaultValue="">
            <option value="" disabled>Selecciona un arma...</option>
            {weapons.map(w => <option key={w} value={w}>{w}</option>)}
          </select>
        </label>

        <button type="submit">Registrar en el Gremio</button>
      </form>
    </article>
  );
}