/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restauranteapp.BLL;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import restauranteapp.BLL.exceptions.NonexistentEntityException;
import restauranteapp.DAL.Encomenda;
import restauranteapp.DAL.Entidade;
import restauranteapp.DAL.Estado;
import restauranteapp.DAL.Fornecedor;
import restauranteapp.DAL.Linhaencomenda;
import restauranteapp.DAL.Mesas;
import restauranteapp.DAL.Pedido;
import restauranteapp.DAL.Produtoementa;
import restauranteapp.DAL.Reserva;
import restauranteapp.DAL.Stockproduto;

/**
 *
 * @author kevin
 */
public class MenuJpaController {
    
    private EntityManagerFactory em;
    private PedidoJpaController pc;
    private MesasJpaController mc;
    private ProdutoementaJpaController pec;
    private EncomendaJpaController ec;
    private StockprodutoJpaController spc;
    private EstadoJpaController estc;
    private ReservaJpaController rc;
    private FornecedorJpaController fc;
    private LinhaencomendaJpaController lc;
    
    public MenuJpaController() {
        this.em = Persistence.createEntityManagerFactory("RestauranteAppPU");
        this.mc = new MesasJpaController(em);
        this.pc = new PedidoJpaController(em);
        this.pec = new ProdutoementaJpaController(em);
        this.ec = new EncomendaJpaController(em);
        this.spc = new StockprodutoJpaController(em);
        this.estc = new EstadoJpaController(em);
        this.rc = new ReservaJpaController(em);
        this.fc = new FornecedorJpaController(em);
        this.lc = new LinhaencomendaJpaController(em);
    }
    
    public Encomenda findEncomendaId(int id){
        return ec.findEncomenda(id);
    }
    
    public Estado findEstadoId(int id){
       EntityManager em2 = em.createEntityManager();
       try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
          Query q =   em2.createNamedQuery("Estado.findByIdEstado", Estado.class);
          q.setParameter("idEstado", id);
           return (Estado) q.getSingleResult();
         }catch(NoResultException e){
            return null;
        } finally {
            em2.close();
        }
    }   
    
    public Pedido findPedidoId(int id){
       EntityManager em2 = em.createEntityManager();
       try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
          Query q =   em2.createNamedQuery("Pedido.findByCodpedido", Pedido.class);
          q.setParameter("codpedido", id);
           return (Pedido) q.getSingleResult();
         }catch(NoResultException e){
            return null;
        } finally {
            em2.close();
        }
    }
    
    public Stockproduto findStockProdutoNome(String nome){
       EntityManager em2 = em.createEntityManager();
       try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Stockproduto.class));
          Query q =   em2.createNamedQuery("Stockproduto.findByNome", Stockproduto.class);
          q.setParameter("nome", nome);
           return (Stockproduto) q.getSingleResult();
         }catch(NoResultException e){
            return null;
        } finally {
            em2.close();
        }
    }
    
    public Fornecedor findFornecedorNome(String nome){
       EntityManager em2 = em.createEntityManager();
       try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fornecedor.class));
          Query q =   em2.createNamedQuery("Fornecedor.findByNome", Fornecedor.class);
          q.setParameter("nome", nome);
           return (Fornecedor) q.getSingleResult();
         }catch(NoResultException e){
            return null;
        } finally {
            em2.close();
        }
    }
    
    public List<Produtoementa> findProdutoByCategoria(String categoria){
        EntityManager em2 = em.createEntityManager();
       try {
          CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
          cq.select(cq.from(Produtoementa.class));
          Query q =   em2.createNamedQuery("Produtoementa.findByCategoria", Produtoementa.class);
          q.setParameter("categoria", categoria);
           return q.getResultList();
         }catch(NoResultException e){
            return null;
        } finally {
            em2.close();
        }
    }
    
    public Mesas findMesaId(int id){
       EntityManager em2 = em.createEntityManager();
       try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mesas.class));
          Query q =   em2.createNamedQuery("Mesas.findByIdMesa", Mesas.class);
          q.setParameter("idMesa", id);
           return (Mesas) q.getSingleResult();
         }catch(NoResultException e){
            return null;
        } finally {
            em2.close();
        }
    }
    
    public Reserva findReservaId(int id){
       EntityManager em2 = em.createEntityManager();
       try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reserva.class));
          Query q =   em2.createNamedQuery("Reserva.findByIdReserva", Reserva.class);
          q.setParameter("idReserva", id);
           return (Reserva) q.getSingleResult();
         }catch(NoResultException e){
            return null;
        } finally {
            em2.close();
        }
    }
    
    public Encomenda findEncomendaDescricao(String descricao){
       EntityManager em2 = em.createEntityManager();
       try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Encomenda.class));
          Query q =   em2.createNamedQuery("Encomenda.findByDescricao", Encomenda.class);
          q.setParameter("descricao", descricao);
           return (Encomenda) q.getSingleResult();
         }catch(NoResultException e){
            return null;
        } finally {
            em2.close();
        }
    }
    
    public void updatePedido(Pedido pedido){
        try {
            pc.edit(pedido);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MenuJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MenuJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateReserva(Reserva reserva){
        try {
            rc.edit(reserva);
        } catch (Exception ex) {
            Logger.getLogger(MenuJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateEncomenda(Encomenda encomenda){
        try {
            ec.edit(encomenda);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MenuJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MenuJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Fornecedor> getFornecedores(){
        List<Fornecedor> fornecedores = fc.findFornecedorEntities();
        
        return fornecedores;
    }
    
    public List<Mesas> getMesas(){   
        List<Mesas> mesas = mc.findMesasEntities();
        
        return mesas;
    }
    
    public List<Pedido> getPedidos(){
        List<Pedido> pedidos = pc.findPedidoEntities();
        return pedidos;
    }
    
    public List<Produtoementa> getProdutosEmenta(){   
        List<Produtoementa> produtos = pec.findProdutoementaEntities();
        return produtos;
    }
    
    public List<Stockproduto> getStockProdutos(){
        List<Stockproduto> produtos = spc.findStockprodutoEntities();
        return produtos;
    }
    
    public List<Encomenda> getEncomendas(){      
        List<Encomenda> encomendas = ec.findEncomendaEntities();
        return encomendas;
    }
    
    public List<Estado> getEstados(){
        List<Estado> estados = estc.findEstadoEntities();
        return estados;
    }
    
    public void createEncomenda(Encomenda encomenda){
        ec.create(encomenda);
    }
    
    public void createPedido(Pedido pedido){
        pc.create(pedido);
    }
    
    public void createProdutoEmenta(Produtoementa produto){
        pec.create(produto);
    }
    
    public void createLinhaEncomenda(Linhaencomenda le){
        try {
            lc.create(le);
        } catch (Exception ex) {
            Logger.getLogger(MenuJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
