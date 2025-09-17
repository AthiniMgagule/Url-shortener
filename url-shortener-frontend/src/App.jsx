import { useState } from 'react'
import axios from 'axios'
import {Link, Copy, BarChart3, ExternalLink } from 'lucide-react'
import './App.css'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

function App() {
  const [url, setUrl] = useState('')
  const [shortUrl, setShortUrl] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [copied, setCopied] = useState(false)

  //====== for submitting the url
  const handleSubmit = async (e) => {
    e.preventDefault()
    if(!url.trim()){
      setError('Please enter a URL')
      return
    }

    setLoading(true)
    setError('')

    try{
      const response = await axios.post(`${API_BASE_URL}/shorten`, {
        url: url.trim()
      })

      setShortUrl(response.data.shortUrl)
    } catch(err){
      setError('Failed to shorten URL. Please try again.')
      console.error('Error:', err)
    } finally{
      setLoading(false)
    }
  }

  //====== for copying to clipboard
  const copyToClipboard = () => {
    navigator.clipboard.writeText(shortUrl)
    setShortUrl(true)
    setTimeout(()=>setCopied(false), 2000)
  }

  //====== for resetting
  const resetForm = () => {
    setUrl('')
    setShortUrl('')
    setLoading(false)
    setError('')
    setCopied(false)
  }

  return (
    <>
      <div className = "container">
        <div className = "card">
          <div className = "header">
            <div className = "icon-container">
              <Link className = "icon" />
            </div>
            <h1>URL Shortener</h1>
            <p1>transforms long urls to short urls</p1>
          </div>

          <form onSubmit={handleSubmit} className="form">
            <div className="input-group">
              <input type="text" placeholder = "enter your url here..." valuse = {url} onChange={(e)=> setUrl(e.target.value)} className="input" disabled={loading}/>
              <button type="submit" disable={loading|| !url.trim()} className="button primary">{loading? 'Shortening...' : 'Shorten URL'}</button>
            </div>
            {error && (<div className = "error">{error}</div>)}
          </form>

          {shortUrl && (
          <div className="result">
            <h3>Your shortened URL:</h3>
            <div className="url-container">
              <div className="url-display">
                <ExternalLink size={16} />
                <span>{shortUrl}</span>
              </div>
              <div className="button-group">
                <button
                  onClick={copyToClipboard}
                  className="button secondary"
                >
                  <Copy size={16} />
                  {copied ? 'Copied!' : 'Copy'}
                </button>
                <button
                  onClick={() => window.open(shortUrl, '_blank')}
                  className="button secondary"
                >
                  <BarChart3 size={16} />
                  Visit
                </button>
              </div>
            </div>
            <button onClick={resetForm} className="button outline">
              Shorten Another URL
            </button>
          </div>
        )}
        </div>
      </div>
    </>
  )
}

export default App