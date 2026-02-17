import { useState } from 'react';

export default function Register() {
  const [formData, setFormData] = useState({
    name: '', email: '', password: '', rank: 1, mainWeapon: 'GREAT_SWORD'
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData)
      });

      if (response.ok) {
        alert("✅ Cuenta creada con éxito. ¡Ahora inicia sesión!");
      } else {
        const errorText = await response.text();
        alert("Error al registrarse: " + errorText);
      }
    } catch (error) {
      alert("Error de conexión");
    }
  };

  return (
    <div className="card">
      <h2>Nuevo Cazador</h2>
      <form onSubmit={handleSubmit}>
        <input name="name" placeholder="Nombre" onChange={handleChange} required />
        <input name="email" type="email" placeholder="Email" onChange={handleChange} required />
        <input name="password" type="password" placeholder="Contraseña" onChange={handleChange} required />
        <button type="submit" className="secondary">Registrarse</button>
      </form>
    </div>
  );
}