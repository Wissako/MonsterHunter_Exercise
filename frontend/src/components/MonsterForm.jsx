import { useState } from 'react';

export default function MonsterForm() {
    const [formData, setFormData] = useState({
        name: '', type: '', weakness: '', threatLevel: 1, imageUrl: ''
    });

    const handleChange = (e) => {
        setFormData({...formData, [e.target.name]: e.target.value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        // 1. RECUPERAR EL TOKEN
        const token = localStorage.getItem('jwt_token');

        if (!token) {
            alert("⚠️ No estás logueado. Inicia sesión.");
            return;
        }

        try {
            const res = await fetch('http://localhost:8080/monsters', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // 2. ENVIAR LA LLAVE (Importante: 'Bearer ' + token)
                    'Authorization': `Bearer ${token}` 
                },
                body: JSON.stringify(formData)
            });

            if(res.ok) {
                alert("✅ ¡Monstruo registrado con éxito!");
                window.location.reload(); 
            } else {
                alert(`❌ Error al guardar: ${res.status}`);
            }
        } catch (error) {
            console.error(error);
            alert("Error de conexión");
        }
    };

    // Estilos rápidos
    const inputStyle = { backgroundColor: '#000000', border: 'none', padding: '10px', borderRadius: '4px', width: '100%', marginBottom: '10px' };
    const labelStyle = { color: '#000000', display: 'block', marginBottom: '5px', fontWeight: 'bold' };

    return (
        <article style={{ backgroundColor: '#2d3436', border: '2px solid #5d4037', borderRadius: '10px', padding: '20px', color: 'white' }}>
            <h3 style={{color: '#ff7675', borderBottom: '1px solid #636e72', paddingBottom: '10px'}}>🦖 Avistar Monstruo</h3>
            <form onSubmit={handleSubmit}>
                <label style={labelStyle}>Nombre</label>
                <input name="name" onChange={handleChange} style={inputStyle} required />
                <div className="grid">
                    <div><label style={labelStyle}>Tipo</label><input name="type" onChange={handleChange} style={inputStyle} required /></div>
                    <div><label style={labelStyle}>Debilidad</label><input name="weakness" onChange={handleChange} style={inputStyle} required /></div>
                </div>
                <label style={labelStyle}>Amenaza: {formData.threatLevel}</label>
                <input type="range" name="threatLevel" min="1" max="10" value={formData.threatLevel} onChange={handleChange} style={{width: '100%', marginBottom: '15px'}} />
                <label style={labelStyle}>Imagen URL</label>
                <input type="url" name="imageUrl" onChange={handleChange} style={inputStyle} />
                <button type="submit" style={{backgroundColor: '#c0392b', border: 'none', width: '100%', padding: '10px', color: 'white', fontWeight: 'bold', cursor: 'pointer'}}>Registrar</button>
            </form>
        </article>
    );
}